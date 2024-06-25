
import { prisma } from "@/utils/connect";
import bcrypt from "bcryptjs";
import { NextRequest, NextResponse } from "next/server";


export const POST = async (req: NextRequest) => {
    const {name, email, password} = await req.json();
    const hashedPassword = await bcrypt.hash(password, 5);

    try {
        await prisma.user.create({
            data: { name: name, email: email, password: hashedPassword }
        });
        return new NextResponse("User has been created", {
            status: 201,
        });
    } catch (err: any) {
        return new NextResponse(err.message, {
            status: 500,
        });
    }
}