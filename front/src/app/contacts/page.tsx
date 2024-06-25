
const ContactsPage = async () => {
  //md:flex-row
  return (
    <div className="p-4 lg:px-20 xl:px-40 h-[calc(100vh-6rem)] md:h-[calc(100vh-9rem)] flex 
    flex-col  items-center justify-between">

      {/* contacts container */}
      <div className="flex flex-col h-full p-2">
        <h1 className="font-bold text-xl text-red-500 uppercase mb-4 p-2"> Contacts</h1>
        {/* tel numbers container*/}
        <div className="flex flex-row gap-2 text-red-500 uppercase mb-4">
          <div className="flex items-center ">tel -</div>
          <div className="flex flex-col">
            <a href="tel:0672345678">067-234-56-78</a>
            <a href="tel:0932345678">093-234-56-78</a>
            <a href="tel:0992345678">099-234-56-78</a>
          </div>

        </div>
        {/* email container*/}
        <div className="flex flex-row gap-2 text-red-500  mb-4">
          <div className="flex items-center">email -</div>
          <div >
            <a className="underline" href="email:mail2@mail3.com">
              mail2@mail3.com
              </a>

          </div>

        </div>
        {/* working hours*/}
        <div className="flex flex-row gap-2 text-red-500 uppercase mb-4">
          <div className="flex items-start flex-col ">
            <div>monday - friday</div>
            <div>holidays</div>
          </div>
          <div >
            <div>(08:00 - 19:30)</div>
            <div>(09:00 - 21:30)</div>
            
          </div>

        </div>
        {/* address container*/}
        <div className="flex flex-col justify-end gap-2 text-red-500  mb-4">
          <div className="flex items-center ">We are in</div>
          <div className="flex flex-col">
            <p>Kropyvnytskyi</p>
            <p>Shevchenka str. 12</p>
            <p>post code 27700</p>
          </div>

        </div>
      </div>
    </div>
  );
};

export default ContactsPage;