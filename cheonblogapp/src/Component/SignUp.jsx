import React ,{useState} from 'react';
import {useNavigate} from "react-router-dom";


function SignUp(){
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


    };

    return(
        <div className="SignUp">
            <p className="text-gray-500 p-3">회원가입 후 서비스 이용이 가능합니다.</p>
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
                    <button type="submit" className="border rounded-[2rem] w-[150px] bg-black text-white">회원가입</button>
                </div>

            </form>
        </div>
    );
}


export default SignUp;