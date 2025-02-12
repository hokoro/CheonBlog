import React, {useState} from 'react';
import {useNavigate} from "react-router-dom";

function validationEmail(email){    // 이메일 규정에 맞는지 체크
    const regax =/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return regax.test(email);
}

function Login(){
    const [emailValid , setEmailValid] = useState(false);
    const [passwordValid , setPasswordValid] = useState(false);
    const navigate = useNavigate();

    const [formData , setFormData] = useState({
        email: "",
        password: ""
    })

    const handleChange = (event) =>{
        const {name, value} = event.target;
        setFormData((prevData) => ({
            ...prevData,
            [name] : value
        }));
        if(name==='email'){
            if(validationEmail(value)){
                setEmailValid(true);
            }else{
                setEmailValid(false);
            }
        }
        if(name==='password'){
            if(value.length > 0){
                setPasswordValid(true);
            }else{
                setPasswordValid(false);
            }
        }
    }

    const handleLoginClick = async (event) =>{
        event.preventDefault(); // 폼 제출 및 새로고침 방지

        try{
            const response = await fetch('https://localhost:7942/api/user/login',{
                method:'POST',
                headers:{
                    'Content-Type':'application/json',
                },
                credentials:'include',
                body:JSON.stringify({
                    email: formData.email,
                    password: formData.password,
                }),
            });
            const data = await response.json();
            if(response.ok){
                console.log(data.message);
                sessionStorage.setItem("accessToken" , 'Bearer '+data.accessToken);
                document.cookie = `refreshToken=${data.refreshToken}; HttpOnly; Secure; path=/; Max-Age=3600;`;
                navigate('/');
            }else{
                console.log(data.message);
            }

        }catch (error){
            console.log("인증 요청중 오류 발생:" , error);
        }

    }

    return(
        <div className="Login">
            <p className="text-gray-500 p-3">로그인</p>
            <form className="m-[2rem]">
                <div className="m-[2rem]">
                    <input type="email" name="email" placeholder="이메일" value={formData.email} onChange={handleChange}
                           className="border p-[1rem] rounded-2xl w-[300px]"/>
                </div>
                <div className="m-[2rem]">
                    <input type="password" name="password" placeholder="비밀번호" value={formData.password} onChange={handleChange}
                           className="border p-[1rem] rounded-2xl w-[300px]"/>
                </div>
                {emailValid && passwordValid &&
                    <div className="m-[2rem]">
                        <button type="button"
                                className="border rounded-[2rem] w-[150px] h-[40px] bg-black text-white hover:text-orange-500" onClick={handleLoginClick}>
                            Login
                        </button>
                    </div>
                }
            </form>

        </div>
    )
}

export default Login;