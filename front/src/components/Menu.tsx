'use client'
import Image from "next/image"
import Link from "next/link";
import { useState } from "react"
import CartIcon from "./CartIcon";
import { signOut, useSession } from "next-auth/react";
import { useCartStore } from "@/utils/store";

const links = [
  { id: 1, title: "Homepage", url: "/" },
  { id: 2, title: "Menu", url: "/menu" },
  { id: 3, title: "Contacts", url: "/contacts" },
  { id: 4, title: "orders", url: "/orders" }
];

export default function Menu() {
  const [open, setOpen] = useState(false)
  const { status } = useSession();
  const { clearCart } = useCartStore()
  const handleLogOut = () => {
    signOut()
    clearCart()
  }

  return (
    <div>
      {!open ? (<Image className="cursor-pointer" src="/open.png" alt="" width={20} height={20} onClick={() => {
        setOpen(true);
      }} />
      ) : (
        <Image src="/close.png" alt="" width={20} height={20} onClick={() => setOpen(false)} />
      )}
      {open && <div className="bg-red-500 text-white absolute left-0 top-24 w-full h-[calc(100vh-6rem)]
             flex flex-col gap-8 items-center justify-center text-3xl z-10">
        {links.map((item) => (
          <Link href={item.url} key={item.id} onClick={() => setOpen(false)}>
            {item.title}
          </Link>))}
        {/* LONG WAY */}
        {status === "authenticated" ? (
          <div>
            <span className="ml-4 cursor-pointer" onClick={handleLogOut}>Logout</span>
          </div>
        ) : (
          <Link onClick={() => { setOpen(false) }} href="/login">Login</Link>
        )}
        <div onClick={() => { setOpen(false) }}>
          <CartIcon />
        </div>
      </div>}
    </div>
  )
}