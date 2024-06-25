
import { AddProductButton } from "@/components/AddProductButton";
import { MenuType } from "@/types/types";
import Link from "next/link";
import React from "react";

const getData = async () => {
  console.log("menu fetch --- ");
  console.log(process.env.NEXT_PUBLIC_BASE_URL);
  
  const res = await fetch(`${process.env.NEXT_PUBLIC_BASE_URL}/api/categories`)
   
  if (!res.ok) {
    throw new Error("Failed fetch")
  }
  return res.json()
}

const MenuPage = async () => {
  
  const menu: MenuType = await getData();
  
  
  return (
    <div className="flex flex-col h-[calc(100vh-6rem)]">
      <AddProductButton/>
      <div className="p-4 lg:px-20 xl:px-40 h-[calc(100vh-6rem)] md:h-[calc(100vh-9rem)] flex 
    flex-col md:flex-row items-center ">

        {menu.map((category) => (
          <Link
            href={`/menu/${category.slug}`}
            key={category.id}
            className="w-full h-1/3 bg-cover p-8 md:h-1/2"
            style={{ backgroundImage: `url(${category.img})` }}
          >
            <div className={`text-${category.color} w-2/3 h-full flex flex-col justify-around`}>
              <h1 className="uppercase  font-bold text-3xl">{category.title}</h1>
              <p className="text-sm my-8 md:my-1 h-[60%] overflow-hidden">{category.desc}</p>
              <button
                className={`hidden 2xl:block bg-${category.color} text-2xl 
              text-${category.color === "black" ? "white" : "red-500"}
               py-2 px-4 rounded-md`}>Explore</button>
            </div>
          </Link>
        ))}
      </div>
    </div>);
};

export default MenuPage;



