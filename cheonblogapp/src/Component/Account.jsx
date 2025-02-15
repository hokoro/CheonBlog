import React , {useEffect , useState} from 'react';
import {useNavigate}  from "react-router-dom";



function Account(){

    const navigate = useNavigate();
    const isAuthentication = sessionStorage.getItem('accessToken');

    const [formData , setFormData] = useState({
        email:"",
        name:"",
    })

    useEffect(() => {   // 로그인 정보가 없을 경우 접근 금지
        if(!isAuthentication){
            alert('로그인이 필요합니다.');
            navigate('/login');
        }
    }, [isAuthentication , navigate]);

    useEffect(() => {
        if(isAuthentication){
            fetchUserInfo();
        }
    }, [isAuthentication]);

    const fetchUserInfo = async () =>{
        try{
            const response = await fetch("https://localhost:7942/api/user/detail",{
                method:'POST',
                headers:{
                    'Content-Type':'application/json',
                },
                body:JSON.stringify({
                    accessToken: isAuthentication
                }),
            });
            const data = await response.json();
            if(response.ok){
                console.log(data.email);
                console.log(data.name);
                setFormData({
                    email: data.email,
                    name: data.name
                });
            }else{
                console.log("유저 정보를 불러올 수 없습니다.")
                navigate('/');
            }

        }catch (error){
            console.error("회원 정보를 불러오는 중: " , error);
        }
    }

    return(
        <div className="Account">
            <p className="text-gray-500 p-3">계정 관리</p>
            <form className="m-[2rem]">
                <div className="m-[2rem]">
                    <p className="text-1xl font-bold text-gray-700 p-5">
                        {formData.name}
                    </p>
                </div>
                <div className="m-[2rem]">
                    <p className="text-1xl font-bold text-gray-700 p-5">
                        {formData.email}
                    </p>
                </div>
                <div className="m-[2rem] flex gap-4 justify-center">
                    <button type="button"
                            className="border rounded-[2rem] w-[150px] h-[40px] bg-black text-white hover:text-orange-500"
                    >Update
                    </button>
                    <button type="button"
                            className="border rounded-[2rem] w-[150px] h-[40px] bg-black text-white hover:text-orange-500"
                    >Delete
                    </button>
                </div>
            </form>
        </div>
    )
}


export default Account;