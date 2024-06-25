import { prisma } from "@/utils/connect";
import { NextRequest, NextResponse } from "next/server";

export async function PUT(
  request: NextRequest,
  { params }: { params: { intentId: string } }) {
  const { intentId } = params;
  
  //console.log('\x1b[33m%s\x1b[0m',  intentId);
  // console.log('count: %2d', i);
  
  try {
    await prisma.order.update({
      where: {
        intent_id: intentId,
      },
      data: { status: "Being prepared!" },
    });
    return new NextResponse(
      JSON.stringify({ message: "Order has been updated" }),
      { status: 200 }
    );
  } catch (err) {
    console.log(err , "intend update fail CHECKOUT");
    return new NextResponse(
      JSON.stringify({ message: "Something went wrong!" }),
      { status: 500 }
    );
  }
}