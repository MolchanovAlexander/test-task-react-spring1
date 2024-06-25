
"use client"
import { signIn, useSession } from "next-auth/react";
import Image from "next/image";
import Link from "next/link";
import { useRouter } from "next/navigation";
import React, { useEffect, useState } from "react";
import { InputsReg } from "@/types/types";

const LoginPage = () => {
  const { data, status } = useSession()
  const router = useRouter()
  useEffect(() => {
    if (status === "authenticated") {
      router.push("/")
    }

  }, [ router,status])
  const [fetched, setFetched] = useState(true)
  const [inputs, setInputs] = useState<InputsReg>({
    name: "",
    email: "",
    password: "",
  });
  const [error, setError] = useState(null);
  if (status === "loading") {
    return <p>Loading...</p>
  }
  const handleSubmit = async (e: React.SyntheticEvent) => {
    e.preventDefault();
    
    const res = signIn("credentials", {
      email : inputs.email,
      password: inputs.password,
    });
    console.log(res);
    
  };
  const handleChange =
    (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
      setInputs((prev) => {
        return { ...prev, [e.target.name]: e.target.value };
      });
    };


  return (
    <div className=" h-full  flex 
    items-center justify-center p-4">
      {/* BOX */}
      <div className=" h-full shadow-2xl rounded-md overflow-hidden flex flex-col 
      md:flex-row  md:w-full lg:w-[60%] 2xl:w-1/2">
        {/* IMAGE CONTAINER */}
        <div className="relative hidden  w-full md:block  md:w-1/2 ">
          <Image src="/loginBg.png" alt="" fill className="object-cover" />
        </div>
        {/* FORM CONTAINER */}
        <div className=" p-10 flex flex-col   gap-8 md:w-1/2">
          <h1 className="font-bold text-xl xl:text-3xl">Welcome</h1>
          <form onSubmit={handleSubmit} className="flex gap-5 w-full flex-col">

            <input
              type="text"
              placeholder="Email"
              required
              name="email"
              className="bg-transparent rounded ring-2 ring-gray-600 p-5 font-bold	"
              onChange={handleChange}
            />
            <input
              type="password"
              placeholder="Password"
              required
              name="password"
              className="bg-transparent rounded ring-2 ring-gray-600 p-5 font-bold"
              onChange={handleChange}
            />
            <button  onClick={() => setFetched(false)} className="bg-[#EF4444] rounded text-white p-5 font-bold">Login</button >
            {error && "Something went wrong!"}
          </form>
          <p>Log into your account or <Link className="underline" href="/register">create</Link> a new one using social buttons</p>
          <button className="flex gap-4 p-4 ring-2 ring-orange-300 rounded-md hover:bg-orange-300 "
            onClick={() => signIn("google")}>
            <Image
              src="/google.png"
              alt=""
              width={20}
              height={20}
              className="object-contain"
            />
            <span>Sign in with Google</span>
          </button>
          <button className="flex gap-4 p-4 ring-2 ring-blue-300 rounded-md hover:bg-blue-300"
            onClick={() => signIn("facebook")}>
            <Image
              src="/facebook.png"
              alt=""
              width={20}
              height={20}
              className="object-contain"
            />
            <span>Sign in with Facebook</span>
          </button>
          <p className="text-sm">
            Have a problem?<Link className="underline" href="/contacts"> Contact us</Link>
          </p>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;