import React ,{useState} from 'react';
import {useNavigate} from "react-router-dom";


function validationEmail(email){    // 이메일 규정에 맞는지 체크
    const regax =/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return regax.test(email);
}


function SignUp(){
    const navigate = useNavigate(); // 회원가입 성공시 메인 홈페이지나 로그인 페이지로 랜더링
    const [emailValid , setEmailValid] = useState(false);
    const [emailMessage , setEmailMessage] = useState("");
    const [formData , setFormData] = useState({
        name:"",
        email:"",
        password:""
    })

    const handleChange = (event) =>{
        const {name , value} = event.target;
        setFormData((prevData) => ({
            ...prevData,
            [name] : value
        }));
        if(name==='email'){
            if(validationEmail(value)){
                setEmailValid(true);
                setEmailMessage("이메일 형식이 일치합니다.");
            }else{
                setEmailValid(false);
                setEmailMessage("");
            }
        }


    };

    return(
        <div className="SignUp">
            <p className="text-gray-500 p-3">회원가입 후 서비스 이용이 가능합니다.</p>
            {emailValid && <p className="text-gray-500 p-3">{emailMessage}</p> }
            <form className="m-[2rem]">
                <div className="m-[2rem]">
                    <input type="text" name="name" value={formData.name} onChange={handleChange} placeholder="이름"
                           className="border p-[1rem] rounded-2xl w-[300px]"/>
                </div>
                <div className="m-[2rem]">
                    <input type="email" name="email" value={formData.email} onChange={handleChange} placeholder="이메일"
                           className="border p-[1rem] rounded-2xl w-[300px]"/>
                </div>
                <div className="m-[2rem]">
                    <input type="password" name="password" value={formData.password} onChange={handleChange} placeholder="비밀번호" className="border p-[1rem] rounded-2xl w-[300px]"/>
                </div>
                <div className="m-[2rem]">
                    <button type="submit" className="border rounded-[2rem] w-[150px] h-[40px] bg-black text-white hover:text-orange-500">SignUp</button>
                </div>

            </form>
        </div>
    );
}


export default SignUp;