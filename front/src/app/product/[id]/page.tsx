import Price from "@/components/Price";
import { ProductType } from "@/types/types";
import DeleteButton from "@/components/DeleteButton"
import Image from "next/image";
import React from "react";


const getData = async (id: string) => {

  const res = await fetch(`${process.env.NEXT_PUBLIC_BASE_URL}/api/products/${id}`)
  if (!res.ok) {
    throw new Error("Failed")
  }
  // read the comment below 
  const data = await res.json()

  return {
    ...data,
    price: +data.price
  }

}
// nextjs way to dynamic change of metadata
export async function generateMetadata({ params }: any) {
  const singleProduct: ProductType = await getData(params.id)
  return {
    title: singleProduct.title,
    description: singleProduct.desc || null
  }
}

const SingleProductPage = async ({ params }: { params: { id: string } }) => {

  const singleProduct: ProductType = await getData(params.id)

  return (
    <div className="p-4 lg:px-20 xl:px-40 h-screen flex flex-col justify-around text-red-500 md:flex-row md:gap-8 md:items-center">
      {/* IMAGE CONTAINER */}
      {singleProduct.img && (
        <div className="relative w-full h-1/2 md:h-[70%]">
          <Image
            src={singleProduct.img}
            alt=""
            className="object-contain"
            fill
          />
        </div>
      )}
      {/* TEXT CONTAINER */}
      <div className="h-1/2 flex flex-col gap-4 md:h-[70%] md:justify-center md:gap-6 xl:gap-8">
        <h1 className="text-3xl font-bold uppercase xl:text-5xl">{singleProduct.title}</h1>
        <p>{singleProduct.desc}</p>
        <Price product={singleProduct} />
      </div>
      <DeleteButton id={singleProduct.id} />
    </div>
  );
};

export default SingleProductPage;
/* this is response res.json()
 where price is string passed  in to <Price> 
need to transform type to number because 
some troubles in db response from Decimal to JS number
{
  id: 'clmsutasn0001hjs07ox7da8g',
  createdAt: '2023-09-21T07:31:05.832Z',
  title: 'pasta goya',
  desc: 'desfrfrfrfr',
  img: '/temporary/p4.png',
  price: '32',
  isFeatured: true,
  options: [
    { title: 'small', additionalPrice: 0 },
    { title: 'dno', additionalPrice: 2 }
  ],
  catSlug: 'pastas'
}*/