"use client";
import { InputsReg } from "@/types/types";
import Link from "next/link";
import { useRouter } from "next/navigation";
import React, { useState } from "react";
import styles from "./page.module.css";

const Register = () => {
  const [error, setError] = useState(null);
  const [inputs, setInputs] = useState<InputsReg>({
    name: "",
    email: "",
    password: "",
  });

  const router = useRouter();
  const handleSubmit = async (e: React.SyntheticEvent) => {
    e.preventDefault();

    
    try {
      const res = await fetch(`${process.env.NEXT_PUBLIC_BASE_URL}/api/auth/register`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          ...inputs,
        }),
      });
      res.status === 201 && router.push("/login?success=Account has been created");
    } catch (err: any) {
      setError(err);
      console.log(err);
    }
  };
  const handleChange =
    (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
      setInputs((prev) => {
        return { ...prev, [e.target.name]: e.target.value };
      });
    };


  return (
    <div className={styles.container}>
      <h1 className={styles.title}>Create an Account</h1>
      <h2 className={styles.subtitle}>Please sign up.</h2>
      <form onSubmit={handleSubmit} className={styles.form}>
        <input
          type="text"
          placeholder="Username"
          required
          name="name"
          className={styles.input}
          onChange={handleChange}
        />
        <input
          type="text"
          placeholder="Email"
          required
          name="email"
          className={styles.input}
          onChange={handleChange}
        />
        <input
          type="password"
          placeholder="Password"
          required
          name="password"
          className={styles.input}
          onChange={handleChange}
        />
        <button className={styles.button}>Register</button>
        {error && "Something went wrong!"}
      </form>
      <span className={styles.or}>- OR -</span>
      <Link className={styles.link} href="/login">
        Login with an existing account
      </Link>
    </div>
  );
};

export default Register;