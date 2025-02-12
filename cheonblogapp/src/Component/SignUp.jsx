import React ,{useState} from 'react';
import {useNavigate} from "react-router-dom";


function validationEmail(email){    // 이메일 규정에 맞는지 체크
    const regax =/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return regax.test(email);
}

function validationPassword(password){
    const regax = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/;
    return regax.test(password);
}


function SignUp(){
    const navigate = useNavigate(); // 회원가입 성공시 메인 홈페이지나 로그인 페이지로 랜더링
    const [nameValid , setNameValid] = useState(false); // 이름에 입력 여부
    const [emailValid , setEmailValid] = useState(false);   // 이메일 규정에 어긋나지 않았는지
    const [emailCheckValid , setEmailCheckValid] = useState(false); // 이메일 인증 여부
    const [passwordValid , setPasswordValid] = useState(false); // 패스워드 규정이 어긋나지 않았는지
    const [passwordCheckValid , setPasswordCheckValid] = useState(false); // 비밀번호 확인이 맞는지
    const [emailMessage , setEmailMessage] = useState("");  // 이메일 규정에 어긋날 경우 뜨는 메세지
    const [emailButtonClick , setEmailButtonClick] = useState(false);   // 이메일 버튼 클릭 여부


    const [formData , setFormData] = useState({
        name:"",
        email:"",
        password:"",
        passwordCheck:"",
        emailVerification: ""
    })

    const handleChange = (event) =>{
        const {name , value} = event.target;
        setFormData((prevData) => ({
            ...prevData,
            [name] : value
        }));
        if(name === 'name'){
            if(value.length >= 2){
                setNameValid(true);
            }else{
                setNameValid(false);
            }
        }
        if(name==='email'){
            if(validationEmail(value)){
                setEmailValid(true);
                setEmailMessage("이메일 형식이 일치합니다.");
            }else{
                setEmailValid(false);
                setEmailMessage("");
            }
        }
        if(name==='password'){
            if(validationPassword(value)){
                setPasswordValid(true);
            }else{
                setPasswordValid(false);
            }
        }
        if(name==='passwordCheck'){
            if(value === formData.password){
                setPasswordCheckValid(true);
            }else{
                setPasswordCheckValid(false);
            }
        }

        if(name==='emailVerification'){
            if(value === localStorage.getItem("number")){
                setEmailCheckValid(true);
                setEmailMessage("이메일 인증에 성공했습니다.");
            }else{
                setEmailCheckValid(false);
            }
        }


    };
    // 이메일 인증 API 호출
    const handleEmailVerification = async () =>{
        setEmailButtonClick(true);

        try{
            const response = await fetch('https://localhost:7942/api/email/valid',{
                method: 'POST',
                headers:{
                    'Content-Type':'application/json',
                },
               body:JSON.stringify({email: formData.email}),
            });
            const data = await response.json();
            localStorage.setItem("number" , data.number);
            if(response.ok){
                console.log('인증번호 발송' , data);
            }else{
                console.log('발송 실패',data.message);
            }
        }catch (error){
            console.log("인증 요청중 오류 발생:" , error);
        }

    }

    // 회원가입 API 호출
    const handleSignUpClick = async(event) =>{
        event.preventDefault(); // 폼 제출 및 새로고침 방지

        try{
            const response = await fetch('https://localhost:7942/api/user/create',{
                method:'POST',
                headers:{
                    'Content-Type':'application/json',
                },
                body:JSON.stringify({
                    email: formData.email,
                    password: formData.password,
                    name: formData.name
                }),
            });
            const data = await response.json();
            if(response.ok){
                console.log(data.message);
                navigate('/');
            }else{
                console.log(data.message);
            }

        }catch (error){
            console.log("인증 요청중 오류 발생:" , error);
        }
    }

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
                           className="border p-[1rem] rounded-2xl w-[300px] pr-[1rem]"/>
                </div>
                <div className="m-[2rem]">
                    <input type="password" name="password" value={formData.password} onChange={handleChange}
                           placeholder="비밀번호" className="border p-[1rem] rounded-2xl w-[300px]"/>
                </div>
                <div className="m-[2rem]">
                    <input type="password" name="passwordCheck" value={formData.passwordCheck} onChange={handleChange}
                           placeholder="비밀번호 확인" className="border p-[1rem] rounded-2xl w-[300px]"/>
                </div>
                {emailValid && !emailButtonClick &&<div className="m-[2rem]">
                    <button type="submit"
                            className="border rounded-[2rem] w-[150px] h-[40px] bg-black text-white hover:text-orange-500" onClick={handleEmailVerification}>이메일
                        인증
                    </button>
                </div>
                }
                {emailButtonClick && <div className="m-[2rem]">
                    <div className="m-[2rem]">
                        <input type="text" name="emailVerification" value={formData.emailVerification}
                               onChange={handleChange}
                               placeholder="인증번호 입력" className="border p-[1rem] rounded-2xl w-[300px]"/>
                    </div>
                </div>
                }

                {nameValid && emailValid && passwordValid && passwordCheckValid && emailCheckValid &&
                    <div className="m-[2rem]">
                        <button type="button"
                                className="border rounded-[2rem] w-[150px] h-[40px] bg-black text-white hover:text-orange-500" onClick={handleSignUpClick}>SignUp
                        </button>
                    </div>
                }
            </form>
        </div>
    );
}


export default SignUp;