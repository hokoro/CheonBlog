import React from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";


function Sidebar(){
    const [menuButtonCheckValid , setMenuButtonCheckValid] = useState(true);    // 메뉴 클릭 여부 false - > 사이드 바 사라짐 / true -> 사이드 바 생성 

    const handleMenuClick = () =>{
        if(menuButtonCheckValid === true){
            setMenuButtonCheckValid(false);
        }else{
            setMenuButtonCheckValid(true);
        }
    }

    return(
        <>
        {!menuButtonCheckValid && (
            <div className="m-[8px] md:w-[15rem] flex flex-col">
                <div className="flex items-start px-[4px] py-[8px]">
                    <span className="material-symbols-outlined hover:scale-125 transition-transform duration-200">
                        <button type="button" onClick={handleMenuClick}>
                            double_arrow
                        </button>
                    </span>
                </div>
            </div>
        )}
        <div className={`fixed top-0 left-0 h-full bg-[#FAF3E0] transition-transform duration-500 ease-in-out ${menuButtonCheckValid ? "translate-x-0" : "-translate-x-full"} md:w-[15rem] flex flex-col z-20`}>
            <div className="flex items-start m-[8px] gap-[60px]">
                <div className="px-[4px] py-[8px]">
                    <p className="text-1xl font-bold">hokoro123</p>
                </div>
                <div className="px-[4px] py-[8px] flex gap-[10px]">
                    <span className="material-symbols-outlined hover:scale-125 transition-transform duration-200">
                        <button type="button" onClick={handleMenuClick}>
                            menu
                        </button>
                    </span>
                    <span className="material-symbols-outlined hover:scale-125 transition-transform duration-200">
                        <button type="button">
                            note_add
                        </button>
                    </span>
                </div>
            </div>
            <hr/>
            <div className="m-[8px] flex gap-[10px]">
                <span className="material-symbols-outlined px-[4px] py-[8px]">
                    search
                </span>
                <p className="px-[4px] py-[8px]">문서 검색</p>
            </div>
            <div className="m-[8px] flex gap-[10px]">
                <span class="material-symbols-outlined px-[4px] py-[8px]">
                    home
                </span>
                <p className="px-[4px] py-[8px]">홈</p>
            </div>
        </div>
        </>
    );
}

export default Sidebar;