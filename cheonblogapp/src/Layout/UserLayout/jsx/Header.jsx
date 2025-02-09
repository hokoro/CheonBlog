import React from 'react';
import {useNavigate} from "react-router-dom";

function Header(){

    const navigate = useNavigate();


    return (
        <header className="userlayout-header m-[2rem]">
            <h1 className="text-[1.5rem] p-[2rem] font-bold hover:text-orange-500" onClick={() => navigate('/')}>
                CheonBlog
            </h1>
        </header>
    );
}

export default Header;