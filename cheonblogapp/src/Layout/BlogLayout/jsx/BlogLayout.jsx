import React from "react";
import { Outlet } from "react-router-dom";
import Sidebar from "./Sidebar";

function BlogLayout(){
    return(
    <div className="flex h-screen flex flex-col md:flex-row">
        <Sidebar/>
        <main>
            <Outlet/>
        </main>

    </div>
    );
}

export default BlogLayout;