import Navbar from '@/components/Navbar'
import Notification from '@/components/Notification'
import './globals.css'
import type { Metadata } from 'next'
//import { Inter } from 'next/font/google'
import Footer from '@/components/Footer'
import AuthProvider from '@/components/AuthProvider'
import QueryProvider from '@/components/QueryProvider'
import { ToastContainer } from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css'


import { TelegramProvider } from "../components/TelegramProvider";



//const inter = Inter({ subsets: ['latin'] })

export const metadata: Metadata = {
  title: 'Newbie restaurant',
  description: 'Food for newbies',
}
//export const dynamic = 'force-dynamic'
export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <html lang="en">
      <body className="">
        <AuthProvider>
          <QueryProvider>
            <TelegramProvider>
            <Notification />
            <Navbar />
            
              {children}
            </TelegramProvider>

            <Footer />
            <ToastContainer position='bottom-right' theme='dark' autoClose={2000} />
          </QueryProvider>
        </AuthProvider>
      </body>
    </html>
  )
}
