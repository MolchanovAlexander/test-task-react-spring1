
import { ProductType } from "@/types/types";
import { f } from "@/utils/NumberFormat";
import Image from "next/image";
import Link from "next/link";
import React from "react";

const getData = async () => {
  
  const res = await fetch(`${process.env.NEXT_PUBLIC_BASE_URL}/api/products`, {
    cache: "no-store"
  });

  if (!res.ok) {
    throw new Error("Failed fetch")
  }
  return res.json()
}
const Featured = async () => {
  const featuredProducts:ProductType[] = await getData()

  return (
    <div className="w-screen overflow-x-scroll text-red-500">
      {/* WRAPPER */}
      <div className="w-max flex">
        {/* SINGLE ITEM */}
        {featuredProducts.map((item) => (
          <div
            key={item.id}
            className="w-screen h-[60vh] flex flex-col items-center 
             p-4 hover:bg-fuchsia-50 transition-all duration-300
              md:w-[50vw] xl:w-[33vw] xl:h-[90vh]">
          
            {/* IMAGE CONTAINER */}
            {item.img && (
              <div className="relative flex-1 w-full hover:rotate-[60deg]
               transition-all duration-500">
                <Image src={item.img} alt="" fill className="object-contain" />
              </div>
            )}
            {/* TEXT CONTAINER */}
            <div className=" flex-1 flex flex-col items-center justify-center text-center gap-4">
              <h1 className="text-xl font-bold uppercase xl:text-2xl 2xl:text-3xl">
                {item.title}
              </h1>
              <p className="p-1 2xl:p-8">{item.desc}</p>
              <span className="text-xl font-bold">${f.format(item.price)}</span>
              <Link  
              href={`/product/${item.id}`} 
              className="bg-red-500 text-white p-2 rounded-md">
                Add to Cart
              </Link>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Featured;
