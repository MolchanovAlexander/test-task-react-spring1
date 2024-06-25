"use client";
// PAY PAY PAY  ------ PAAAYYYYY
import CheckoutForm from "@/components/CheckoutForm";
import { Elements } from "@stripe/react-stripe-js";
import { StripeElementsOptions, loadStripe } from "@stripe/stripe-js";
import { useEffect, useState } from "react";

const stripePromise = loadStripe(
  process.env.NEXT_PUBLIC_STRIPE_PUBLISHABLE_KEY!
);

const PayPage = ({ params }: { params: { id: string } }) => {
  const [clientSecret, setClientSecret] = useState("");
  const { id } = params;
  const [isHydrated, setIsHydrated] = useState(false)

  // Wait till Next.js rehydration completes
  useEffect(() => {
    setIsHydrated(true)
    const makeRequest = async () => {

      try {
        if (isHydrated) {

          const res = await fetch(
            `${process.env.NEXT_PUBLIC_BASE_URL}/api/create-intent/${id}`,
            {
              method: "POST",
            }
          );
          const data = await res.json();
          setClientSecret(data.clientSecret);
        }
      } catch (err) {
        console.log(err);
      }
    };
    makeRequest();
  }, [id, isHydrated]);

  const options: StripeElementsOptions = {
    clientSecret,
    appearance: {
      theme: "stripe"
    }
  }
  return (
    <div>
      {clientSecret && (
        <Elements options={options} key={clientSecret} stripe={stripePromise}>
          {<CheckoutForm />}
        </Elements>
      )}
    </div>
  );
};

export default PayPage;