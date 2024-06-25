import Link from "next/link"
import Menu from "./Menu"
import CartIcon from "./CartIcon";
import Image from "next/image";
import UserLinks from "./UserLinks";
import { UserIcon } from "./UserIcon";


export default function Navbar() {
 

  return (
    <div className="h-12 text-red-500 p-4 flex
       justify-between items-center border-b-2 border-b-red-500 uppercase md:h-24 lg:px-20 xl:px-40">
      {/* Left Links */}
      <div className="hidden md:flex gap-4 flex-1">
        <Link href="/">Homepage</Link>
        <Link href="/menu">Menu</Link>
        <Link href="/contacts">Contact</Link>

      </div>
      {/* Logo */}
      <div className="text-xl md:font-bold flex-1 md:text-center">
        <Link href="/"> Massimo </Link>
      </div>

      {/* Mobile */}
      <div className="md:hidden">
        <Menu />
      </div>
      {/* Right Links */}
      <div className="hidden md:flex gap-4 items-center justify-end ">
        <div className="text-transparent w-[30px] h-[30px]"><UserIcon /></div>
        <div className="md:absolute top-3 r-2 lg:static flex items-center gap-2
         cursor-pointer bg-orange-300 px-1 rounded-md justify-center w-[168px]">
          <Image src="/phone.png" alt="" width={20} height={20} />
          <div>0958756262</div>
        </div>
        <UserLinks />
        <CartIcon />

      </div>
    </div>
  )
}