import React from 'react';
import {useNavigate} from "react-router-dom";

function Footer(){

    const navigate = useNavigate();


    return (
        <footer className="userlayout-footer text-[0.9rem] m-1 p-3">
            <p onClick={() => navigate('/')} className="hover:text-orange-500">
                CheonBlog
            </p>
        </footer>
    );
}

export default Footer;

