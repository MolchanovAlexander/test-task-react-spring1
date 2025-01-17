"use client";
import { f } from "@/utils/NumberFormat";
import { useCartStore } from "@/utils/store";
import { useSession } from "next-auth/react";
import Image from "next/image";
import { useRouter } from "next/navigation";
import React, { useEffect } from "react";

const CartPage = () => {
  const { products, totalItems, totalPrice, removeFromCart } = useCartStore();
  const { data: session } = useSession();
  const router = useRouter();

  useEffect(() => {
    useCartStore.persist.rehydrate();
  }, []);

  // CREATING NEW ORDER - IN ORDERS------------------------------------------
  const handleCheckout = async () => {
    if (!session) {
      router.push("/login");
    } else {
      try {
        const res = await fetch(`${process.env.NEXT_PUBLIC_BASE_URL}/api/orders`, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            price: totalPrice,
            products,
            status: "Not Paid!",
            userEmail: session.user.email,
          }),
        });
        const data = await res.json()
        router.push(`/pay/${data.id}`)
      } catch (err) {
        console.log(err);
      }
    }
  };


  return (
    <div className="h-[calc(100vh-6rem)] md:h-[calc(100vh-9rem)] flex flex-col
     text-red-500 lg:flex-row">
      {/* PRODUCTS CONTAINER 
    justify content interfere to show all div's in container( all products in cart )*/}
      <div className="h-1/2 p-4 flex flex-col  overflow-scroll lg:h-full lg:w-2/3 2xl:w-1/2 lg:px-20 2xl:px-40">
        {/* SINGLE ITEM */}
        {products.map((item) => (
          <div className="flex items-center justify-between mb-4" key={item.id}>
            {item.img && (
              <Image src={item.img} alt="" width={100} height={100} />
            )}
            <div className="">
              <h1 className="uppercase text-xl font-bold">
                {item.title + " X " + item.quantity}
              </h1>
              <span>{item.optionTitle}</span>
            </div>
            <div className="flex gap-1 ">
              <h2 className="font-bold">${f.format(item.price)}</h2>
              <span
                className="cursor-pointer"
                onClick={() => removeFromCart(item)}
              >
                X
              </span>
            </div>
          </div>
        ))}
      </div>
      {/* PAYMENT CONTAINER */}
      <div className="h-1/2 p-4 bg-fuchsia-50 flex flex-col gap-4 
      justify-center lg:h-full lg:w-1/3 2xl:w-1/2 lg:px-10 xl:px-20 2xl:text-xl 2xl:gap-6">

        <div className="flex justify-between ">
          <span className="">Subtotal ({totalItems} items)</span>
          <span className="">${f.format(totalPrice)}</span>
        </div>
        <div className="flex justify-between">
          <span className="">Service Cost</span>
          <span className="">$0.00</span>
        </div>
        <div className="flex justify-between">
          <span className="">Delivery Cost</span>
          <span className="text-green-500">FREE!</span>
        </div>
        <hr className="my-2" />
        <div className="flex justify-between">
          <span className="">TOTAL(INCL. VAT)</span>
          <span className="font-bold">${f.format(totalPrice)}</span>
        </div>
        <button onClick={handleCheckout}
          className="bg-red-500 text-white p-3 rounded-md w-1/2 self-end">
          CHECKOUT
        </button>
      </div>
    </div >
  );
};

export default CartPage;