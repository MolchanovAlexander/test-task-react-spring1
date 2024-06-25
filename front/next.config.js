/** @type {import('next').NextConfig} */

const nextConfig = {
  //output: 'export', 
  reactStrictMode: true,
    images: {
      //unoptimized: true,
      domains: ["res.cloudinary.com"],
      remotePatterns: [
        {
          protocol: 'https',
          hostname: 'lh3.googleusercontent.com',
          port: '',
          pathname: '/a/**',
        },
        {
          protocol: 'https',
          hostname: 'platform-lookaside.fbsbx.com',
          port: '',
          pathname: '/**',
        },
      ],
    },
  };
  
  module.exports = nextConfig;