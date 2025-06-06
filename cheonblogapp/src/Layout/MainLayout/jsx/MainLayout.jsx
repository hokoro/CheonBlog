import React,{useState , useEffect} from 'react';
import {useNavigate} from "react-router-dom";
// 블로그 설명 글에 사용할 문구
const descriptions = [
    "나만의 블로그를 꾸밀 수 있습니다.",
    "블로그 디자인을 자유롭게 수정할 수 있습니다.",
    "새로운 기능으로 나만의 포스트를 작성하세요.",
    "모든 기능을 쉽게 사용할 수 있는 인터페이스!",
]


function MainLayout(){

    const navigate = useNavigate();
    const [descriptionIndex, setDescriptionIndex] = useState(0);
    const [isLogin , setIsLogin] = useState(false);

    useEffect(() =>{
        const interval = setInterval(() =>{
            setDescriptionIndex((prevIndex) =>(prevIndex + 1) % descriptions.length);   // 문구를 표현할 index 설정 나머지 정리 활용
        },3000);    // 3초 마다 변경
        return () => clearInterval(interval);
    } , []);

    useEffect(() => {
        const token = sessionStorage.getItem("accessToken");
        if(token){
            setIsLogin(true);
        }
    }, []);

    const handleLogoutClick = async(event) =>{
        event.preventDefault(); // 폼 제출 및 새로고침 방지

        try{
            const token = sessionStorage.getItem('accessToken');
            const response = await fetch('https://localhost:7942/api/user/logout',{
                method:'POST',
                headers:{
                    'Content-Type':'application/json',
                },
                credentials:'include',
                body:JSON.stringify({
                    accessToken:token
                }),
            });
            const data = await response.json();
            if(response.ok){
                console.log(data.message);
                sessionStorage.removeItem("accessToken");
                setIsLogin(false);
            }else{
                console.log(data.message);
                console.log(sessionStorage.getItem('accessToken'))
            }

        }catch (error){
            console.log("인증 요청중 오류 발생:" , error);
        }
    }



    return(
        <div className="h-screen flex flex-col md:flex-row">
            <div className="md:w-2/3 flex flex-col items-center justify-center bg-orange-200 p-10 text-center">
                <div className="main-title">
                    <h1 className="text-4xl font-bold text-gray-800">Welcome to Cheonblog</h1>
                </div>
                <div className="main-description">
                    <p className="mt-4 text-lg text-gray-600">
                        {descriptions[descriptionIndex]}
                    </p>
                </div>
            </div>
            <div className="md:w-1/3 flex items-center justify-center bg-white p-10">
                {!isLogin&&
                    <div className="text-center items-center justify-center">
                        <div>
                            <button className="p-3 bg-slate-500 rounded-2xl py-2 px-4 m-3 hover:bg-slate-600"
                                    onClick={() => navigate('/Login')}>
                                로그인
                            </button>
                            <button className="p-3 bg-slate-500 rounded-2xl py-2 px-4 m-3 hover:bg-slate-600"
                                    onClick={() => navigate('/SignUp')}>
                                회원가입
                            </button>
                        </div>
                        <div>
                            <button className="p-3 bg-slate-500 rounded-2xl py-2 px-4 m-3 hover:bg-slate-600">
                                비밀번호 찾기 
                            </button>
                            <button className="p-3 bg-slate-500 rounded-2xl py-2 px-4 m-3 hover:bg-slate-600">
                                서비스 소개
                            </button>
                        </div>
                    </div>
       
           
                }
                {isLogin &&
                    <div className="text-center items-center justify-center">
                        <div> 
                            <button className="p-3 bg-slate-500 rounded-2xl py-2 px-4 m-3 hover:bg-slate-600" onClick={() => navigate('/Blog')}>
                                서비스 이용
                            </button>
                            <button className="p-3 bg-slate-500 rounded-2xl py-2 px-4 m-3 hover:bg-slate-600" onClick={handleLogoutClick}>
                                로그아웃
                            </button>
                        </div>
                        <div>
                            <button className="p-3 bg-slate-500 rounded-2xl py-2 px-4 m-3 hover:bg-slate-600" onClick={() => navigate('/Account')}>
                                마이 페이지 
                            </button>
                        </div>
                    </div>
                    
                }
            </div>
        </div>
    );
}

export default MainLayout;