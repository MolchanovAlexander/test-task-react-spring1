"use client"

import { useSession } from "next-auth/react";
import Link from "next/link";

export const AddProductButton = () => {
    const { data: session, } = useSession();
    if (session?.user.isAdmin) {
        return <Link href={"/add"} className="bg-red-500 p-4 text-white w-48 
        rounded-md m-2 self-end text-center">
            Add New Product
        </Link >
    } else {
        return null
    }

}