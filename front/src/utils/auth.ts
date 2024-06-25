import { PrismaAdapter } from "@next-auth/prisma-adapter";
import bcrypt from "bcryptjs";
import { NextAuthOptions, User, getServerSession } from "next-auth";
import CredentialsProvider from "next-auth/providers/credentials";
import FacebookProvider from "next-auth/providers/facebook";
import GoogleProvider from "next-auth/providers/google";
import { prisma } from "./connect";

declare module "next-auth" {
  interface Session {
    user: User & {
      isAdmin: Boolean;
    };
  }
}
declare module "next-auth/jwt" {
  interface JWT {
    isAdmin: Boolean;
    jti: string;
  }
}


const secret = process.env.NEXTAUTH_SECRET

export const authOptions: NextAuthOptions = {
  adapter: PrismaAdapter(prisma),
  
  session: {
    strategy: "jwt"
  },
  
  secret: process.env.NEXTAUTH_SECRET,
  providers: [
    CredentialsProvider({
      id: "credentials",
      name: "Credentials",
      credentials: {
        email: {
          label: "Email",
          type: "text",
        },
        password: {
          label: "Password",
          type: "string"
        },
      },
      async authorize(credentials) {
        // Check if the user exists.
        try {
          await prisma.$connect()
          const user = await prisma.user.findUnique({
            where: { email: credentials?.email, }
          });
          
          if (user?.password === null) {
            const hashedPassword = await bcrypt.hash(credentials?.password!, 5);
            await prisma.user.update({
              where: { email: credentials?.email, },
              data:{ password: hashedPassword }
            })
            return user
          }
          if (user!.password !== null) {
            const isPasswordCorrect = await bcrypt.compare(
              credentials?.password!,
              user!.password
            );

            if (isPasswordCorrect) {
              return user;
            } else {
              throw new Error("Wrong Credentials!");
            }
          } else {
           
            throw new Error("User not found!");
          }
        } catch (err: any) {
          throw new Error(err);
        }finally{
          await prisma.$disconnect()
        }
      },
    }),
    GoogleProvider({
      clientId: process.env.GOOGLE_ID!,
      clientSecret: process.env.GOOGLE_SECRET!,
    }),
    FacebookProvider({
      clientId: process.env.FACEBOOK_CLIENT_ID!,
      clientSecret: process.env.FACEBOOK_CLIENT_SECRET!
    })
  ],
  callbacks: {
    async session({ token, session }) {
      if (token) {
        session.user.isAdmin = token.isAdmin
      }

      // findMany many because userId not unique
      // return [session:{...}]
      const sessionInDb = await prisma.session.findMany({
        where: {
          userId: token.sub,
        }
      })
                  
      if (sessionInDb.length) {
        // update many because userId not unique
        await prisma.session.updateMany({
          where: {
            userId: sessionInDb[0].userId,
          },

          data: {
            sessionToken: token.jti,
            userId: token.sub!,
            expires: session.expires!
          }
        })
      } else {
        await prisma.session.create({
          data: {
            sessionToken: token.jti!,
            userId: token.sub!,
            expires: session.expires!
          }
        })
      }

      return session
    },
    async jwt({ token }) {
      const userInDb = await prisma.user.findUnique({
        where: {
          email: token.email!
        }
      })
      token.isAdmin = userInDb?.isAdmin!

      return token;
    }
  }
};

export const getAuthSession = () => getServerSession(authOptions);