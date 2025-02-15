import React from 'react';


function Delete(){
    return(
        <div className="Delete">
            <div className="m-5">
                <h3 className="text-black-500 p-3 text-2xl font-bold">탈퇴</h3>
            </div>
            <div className="m-5">
                <p className="text-gray-500 p-3">탈퇴 하시겠습니까?</p>
            </div>
            <div className="m-5">
                <button className="border rounded-[2rem] w-[150px] h-[40px] bg-black text-white hover:text-red-500">
                    Delete
                </button>
            </div>
        </div>
    );
}


export default Delete;