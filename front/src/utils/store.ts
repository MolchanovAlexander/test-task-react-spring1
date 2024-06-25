import { ActionTypes, CartType } from "@/types/types";
import { create } from "zustand";
import { persist } from "zustand/middleware";

declare global {
  interface Window {
    store: any;
  }
}

const INITIAL_STATE = {
  products: [],
  totalItems: 0,
  totalPrice: 0,
};

export const useCartStore = create(
  (persist<CartType & ActionTypes>(
    (set, get) => ({
      products: INITIAL_STATE.products,
      totalPrice: INITIAL_STATE.totalPrice,
      totalItems: INITIAL_STATE.totalItems,
      addToCart(item) {
        const products = get().products;
        const productInState = products.find((product) => product.id === item.id);

        if (productInState) {
          const updatedProducts = products.map((product) => {
            if (product.id === productInState.id) {
              return {
                ...item,
                quantity: item.quantity + product.quantity,
                price: Number((item.price + product.price).toFixed(2)),
              }
            } else {
              return product
            }
          }
          );
          set((state) => ({
            products: updatedProducts,
            totalItems: state.totalItems + item.quantity,
            totalPrice: Number((state.totalPrice + item.price).toFixed(2)),
          }));
        } else {
          set((state) => ({
            products: [...state.products, item],
            totalItems: state.totalItems + item.quantity,
            totalPrice: Number((state.totalPrice + item.price).toFixed(2)),
          }));

        }
      },
      removeFromCart(item) {

        set((state) => ({
          products: state.products.filter((product) => product.id !== item.id),
          totalItems: state.totalItems - item.quantity,
          totalPrice: +((state.totalPrice - item.price).toFixed(2)),
        }));
      },
      clearCart() {
        console.log("0000 set");
        set(() => ({
          products: [],
          totalItems: 0,
          totalPrice: 0,
        }));
      },
    }),
    { name: "cart", skipHydration: true }
  ))
);
//window.store = useCartStore;