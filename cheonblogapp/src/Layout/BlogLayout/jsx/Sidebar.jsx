import React from "react";
import { useNavigate } from "react-router-dom";


function Sidebar(){
    return(
        <div className="md:w-[15rem] flex flex-col bg-[#FAF3E0]">
            <div className="flex items-start m-[8px] gap-[60px]">
                <div className="px-[4px] py-[8px]">
                    <p className="text-1xl font-bold">hokoro123</p>
                </div>
                <div className="px-[4px] py-[8px] flex gap-[10px]">
                    <span class="material-symbols-outlined hover:scale-125 transition-transform duration-200">
                        menu
                    </span>
                    <span class="material-symbols-outlined hover:scale-125 transition-transform duration-200">
                        note_add
                    </span>
                </div>
            </div>
            <hr/>
        </div>
    );
}

export default Sidebar;