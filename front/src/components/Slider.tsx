"use client"
import Image from "next/image";
import Link from "next/link";
import React, { useEffect, useState } from "react";
import Slideshow from "./Slideshow";

const data = [
    {
        id: 1,
        title: "always fresh & always crispy & always hot",
        image: "/slide1.png",
    },
    {
        id: 2,
        title: "we deliver your order wherever you are in NY",
        image: "/slide2.png",
    },
    {
        id: 3,
        title: "the best pizza to share with your family",
        image: "/slide3.jpg",
    },
];
const Slider = () => {
    const [currentSlide, setCurrentSlide] = useState(0);
  
    
    
    useEffect(() => {
        const interval = setInterval(() => {
            setCurrentSlide((prev) => (prev === data.length - 1 ? 0 : prev + 1))
            
        },3000)
        return () => clearInterval(interval);
    }, [])

    return (
        <div className="flex flex-col h-[calc(100vh-6rem)] md:h-[calc(100vh-9rem)] lg:flex-row bg-fuchsia-50">
            {/* text container */}
            <div className="flex-1 flex h-full items-center justify-between 
            flex-col gap-8 text-red-500 font-bold  ">
                <h1 className="text-5xl items-center text-center uppercase p-4 md:p-10 md:text-6xl xl:text-7xl">
                    {data[currentSlide].title}
                </h1>
                <Link href={`/menu`} className="bg-red-500 mb-4 self-center justify-self-end text-white py-4 px-8">Order now</Link>
            </div >
            {/* img container */}
            <div className="flex flex-1 relative w-full h-full lg:w-1/2   ">
                {/* <Image src={data[currentSlide].image} alt="" fill className="object-cover" /> */}
                <Slideshow />
            </div>
        </div>
    );
};

export default Slider;