import React, {useCallback, useEffect, useState} from 'react';
import { useNavigate } from 'react-router-dom';


function validationPassword(password){
    const regax = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/;
    return regax.test(password);
}

function Update(){
    const navigate = useNavigate();
    const isAuthentication = sessionStorage.getItem('accessToken');

    const [nameValid , setNameValid] = useState(false); // 이름에 입력 여부
    const [passwordValid, setPasswordValid] = useState(false);  // 비밀번호 여부 
    

    const [formData, setFormData] = useState({
        email:"",
        password:"",
        name:"",
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
        if(name === 'password'){
            if(validationPassword(value)){
                setPasswordValid(true);
            }else{
                setPasswordValid(false);
            }
        }
    };
    const fetchUserInfo = useCallback(async() =>{
        try{
            const response = await fetch("https://localhost:7942/api/user/detail",{
                method: 'POST',
                headers:{
                    'Content-Type':'application/json',
                },
                body:JSON.stringify({
                    accessToken:isAuthentication
                }),
            });
            const data = await response.json();
            if(response.ok){
                setFormData({
                    email:data.email,
                    password:"**********",
                    name:data.name
                });
            }else{
                console.log("유저 정보를 불러올 수 없습니다.");
                navigate('/');
            }

        }catch(error){
            console.error("회원 정보를 불러오는 중: " , error);

        }
    },[isAuthentication , navigate])

    useEffect(() =>{
        if(isAuthentication){
            fetchUserInfo();
        }
    },[isAuthentication , fetchUserInfo]);

    const handleUpdateClick = async(event) =>{
        event.preventDefault();

        try{
            const response = await fetch('https://localhost:3000/api/user/update',{
                method:'PUT',
                headers:{
                    'Content-Type':'application/json',
                },
                body:JSON.stringify({
                    name:formData.name,
                    password:formData.password,
                    accessToken:isAuthentication
                }),
            });
            const data = await response.json();
            if(response.ok){
                console.log(data.message);
                navigate('/');
            }else{
                console.log(data.message);
            }

        }catch(error){
            console.log("업데이트 요청중 실패: ",error);
        }
    }

    return(
      <div className="Update">
          <div className="m-3">
              <p className="text-gray-500 p-3">수정</p>
          </div>
          <div className="m-3">
              <form className="m-[2rem]">
                  <div className="m-[2rem] flex justify-center">
                      <p type="text" name="email" className="border p-[1rem] rounded-2xl w-[300px]">
                        {formData.email}
                      </p>
                  </div>
                  <div className="m-[2rem]">
                      <input type="password" name="password" value={formData.password} onChange={handleChange} className="border p-[1rem] rounded-2xl w-[300px]"/>
                  </div>
                  <div className="m-[2rem]">
                      <input type="text" name="name" value={formData.name} onChange={handleChange} className="border p-[1rem] rounded-2xl w-[300px]"/>
                  </div>
                  {nameValid && passwordValid &&
                    <div className="m-[2rem]">
                        <button type="button"
                            className="border rounded-[2rem] w-[300px] h-[40px] bg-green-700 text-white hover:text-orange-500" onClick={handleUpdateClick}> Update
                        </button>
                    </div>
                  }
              </form>

          </div>


      </div>
    );
}

export default Update;