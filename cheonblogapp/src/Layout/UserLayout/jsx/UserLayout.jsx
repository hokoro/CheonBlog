import React from 'react';
import {Outlet} from 'react-router-dom';
import Header from "./Header";
import Footer from "./Footer";
function UserLayout(){
    return(
        <div className="text-center">
            <Header/>
            <main>
                <Outlet/>
            </main>
            <Footer/>
        </div>
    );

}


export default UserLayout;