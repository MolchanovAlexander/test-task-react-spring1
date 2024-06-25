

import { f } from "@/utils/NumberFormat";
import { prisma } from "@/utils/connect";
import { NextRequest, NextResponse } from "next/server";


// FETCH ALL products
export const GET = async (req: NextRequest) => {
  const { searchParams } = new URL(req.url);
  const cat = searchParams.get("cat");

  try {
    await prisma.$connect()
    const products = await prisma.product.findMany({
      where: {
        ...(cat ? { catSlug: cat } : { isFeatured: true }),
      },
    });
    return new NextResponse(JSON.stringify(products), { status: 200 });
  } catch (err) {
    console.log(err , "-API FETCH ALL products");
    return new NextResponse(
      JSON.stringify({ message: "Something went wrong!" }),
      { status: 500 }
    );
  } finally {
    await prisma.$disconnect()
  }
};
// CREATE SINGLE PRODUCT
export const POST = async (req: NextRequest) => {
  try {
    await prisma.$connect()
    const body = await req.json();
    const product = await prisma.product.create({
      data: { ...body, price: +f.format(body.price) },
    });
    return new NextResponse(JSON.stringify(product), { status: 201 });
  } catch (err) {
    console.log(err, "-API CREATE SINGLE PRODUCT");
    return new NextResponse(
      JSON.stringify({ message: "Something went wrong!" }),
      { status: 500 }
    );
  } finally {
    await prisma.$disconnect()
  }
};
