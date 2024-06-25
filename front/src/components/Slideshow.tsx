// Import Swiper React components
import { Swiper, SwiperSlide } from 'swiper/react';
import Image from 'next/image';
import { Navigation, Pagination, Autoplay,Scrollbar ,EffectFade } from 'swiper';
// Import Swiper styles
import 'swiper/swiper-bundle.min.css'


export default function SlideShow  () {
  return (
    <Swiper 
    loop={true}
    effect={"fade"}
    className='w-full h-full ' 
    modules={[Autoplay, Navigation,Pagination,Scrollbar,EffectFade ]}
    navigation={true}
    scrollbar={{draggable:true}}
    pagination={{clickable:true}}
     autoplay={{
      "delay": 2500,
      "disableOnInteraction": false
    }}
     
      slidesPerView={1}
     // onSlideChange={() => console.log('slide change')}
      onSwiper={(swiper) => console.log(swiper)}
    >
      <SwiperSlide><Image src="/slide1.png" alt=''fill className="object-cover" / ></SwiperSlide>
      <SwiperSlide><Image src="/slide2.png" alt=''fill className="object-cover" / ></SwiperSlide>
      <SwiperSlide><Image src="/slide3.jpg" alt=''fill className="object-cover" / ></SwiperSlide>
      
      
    </Swiper>
  );
};