"use client";
import { useCartStore } from "@/utils/store";
import { useRouter, useSearchParams } from "next/navigation";
import React, { useEffect } from "react";
import ConfettiExplosion from "react-confetti-explosion";

const SuccessPage = () => {

  const searchParams = useSearchParams();
  const payment_intent = searchParams.get("payment_intent");
  const router = useRouter();
  const [isExploding, setIsExploding] = React.useState(false);
  
  
  const { clearCart } = useCartStore()
  useEffect(() => {
    const makeRequest = async () => {
      try {
                
        await fetch(`${process.env.NEXT_PUBLIC_BASE_URL}/api/confirm/${payment_intent}`, {
          method: "PUT",
        });
        
        
        setTimeout(() => {
          router.push("/orders");
          console.log('\x1b[33m%s\x1b[0m', "--- success ---");
          clearCart();
        }, 2000);
      } catch (err) {
        console.log(err);
      }
    };

    makeRequest();setIsExploding(true)
  }, [payment_intent, router]);

  return (
    <>
      <div className="min-h-[calc(100vh-6rem)] md:min-h-[calc(100vh-15rem)] flex items-center justify-center text-center text-2xl text-green-700">
        <p className="max-w-[600px]">
          Payment successful. You are being redirected to the orders page.
          Please do not close the page.
        </p>
        {isExploding && <ConfettiExplosion className="absolute m-auto" />}
      </div>
    </>
  );
};

export default SuccessPage;