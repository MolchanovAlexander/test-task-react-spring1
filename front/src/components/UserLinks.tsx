"use client";

import { useCartStore } from "@/utils/store";
import { signOut, useSession } from "next-auth/react";
import Link from "next/link";
import { useTelegram } from "./TelegramProvider";
import { useEffect } from "react";

const UserLinks = () => {
  const { status } = useSession();
  const { clearCart } = useCartStore()
  const { user, webApp, } = useTelegram();
  console.log(user, webApp, "userlinks log");
  const guf = "guff"
  const handleLogOut = () => {
    signOut()
    clearCart()
  }
  useEffect(()=>{
      webApp?.sendData(guf)
     
      
        
    
  },[guf])
  return (
    <div>
      {status === "authenticated" ? (
        <div>
          <Link href="/orders">Orders</Link>
          <div>
            <Link href="/"className="ml-4 cursor-pointer" onClick={handleLogOut}>Logout</Link>
            <span>{user?.first_name}</span>
            </div>
        </div>
      ) : (
        <Link href="/login">Login</Link>
      )}
    </div>
  );
};

export default UserLinks;