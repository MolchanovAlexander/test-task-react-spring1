import Image from "next/image";
import React from "react";
//import CountDown from "./CountDown"; issues the error text did not match (second on server 13 on client side 14)
// from https://nextjs.org/docs/messages/react-hydration-error this is the solution
import dynamic from 'next/dynamic'
import Link from "next/link";
 
const CountDownNoSSR = dynamic(() => import('./CountDown'), { ssr: false })
 
export  function CountDown() {
  return (
    <div>
      <CountDownNoSSR />
    </div>
  )
}

const Offer = () => {
  return (
    <div className="bg-black h-screen flex flex-col md:flex-row
     md:justify-between md:bg-[url('/offerBg.png')] md:h-[70vh]">
      {/* TEXT CONTAINER */}
      <div className="flex-1 flex flex-col justify-center items-center text-center gap-8 p-6">
        <h1 className="text-white text-4xl font-bold xl:text-6xl">
          Delicious Burger & French Fry
        </h1>
        <p className="text-white xl:text-xl">
          Progressively simplify effective e-toilers and process-centric methods
          of empowerment. Quickly pontificate parallel.
        </p>
        <CountDown />
        <Link href={`/`} className="bg-red-500 text-white rounded-md py-3 px-6">Order Now</Link >
      </div>
      {/* IMAGE CONTAINER */}
      <div className="flex-1 w-full relative md:h-full">
        <Image src="/offerProduct.png" alt="" fill className="object-contain" />
      </div>
    </div>
  );
};

export default Offer;