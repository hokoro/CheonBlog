import React from 'react';
import {useNavigate} from "react-router-dom";


function Delete(){

    const navigate = useNavigate();
    const handleClickDelete = async () =>{
        try{
            const response = await fetch('https://localhost:7942/api/user/delete',{
                method:'POST',
                headers:{
                    'Content-Type':'application/json',
                },
                body:JSON.stringify({
                    accessToken: sessionStorage.getItem('accessToken')
                }),
            });
            const data = await response.json();
            if(response.ok){
                alert(data.message);
                sessionStorage.removeItem('accessToken');
                navigate('/');
            }else{
                alert(data.message);
                navigate('/login');
            }

        }catch (error) {
            console.log("요청중 오류 발생: " , error);
        }
    }


    return(
        <div className="Delete">
            <div className="m-5">
                <h3 className="text-black-500 p-3 text-2xl font-bold">탈퇴</h3>
            </div>
            <div className="m-5">
                <p className="text-gray-500 p-3">탈퇴 하시겠습니까?</p>
            </div>
            <div className="m-5">
                <button className="border rounded-[2rem] w-[150px] h-[40px] bg-black text-white hover:text-red-500" onClick={handleClickDelete}>
                    Delete
                </button>
            </div>
        </div>
    );
}


export default Delete;