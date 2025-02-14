import React , {useEffect , useState} from 'react';
import {useNavigate}  from "react-router-dom";



function Account(){

    const navigate = useNavigate();
    const isAuthentication = localStorage.getItem('accessToken');
    const [userInfo ,setUserInfo] = useState(null);

    useEffect(() => {   // 로그인 정보가 없을 경우 접근 금지
        if(!isAuthentication){
            alert('로그인이 필요합니다.');
            navigate('/login');
        }
    }, [isAuthentication , navigate]);

    return(
        <div className="Account">
            <p className="text-gray-500 p-3">계정 관리</p>
            <form className="m-[2rem]">
                <div className="m-[2rem]">

                </div>
                <div className="m-[2rem]">

                </div>
                <div className="m-[2rem]">

                </div>
            </form>
        </div>
    )
}


export  default Account;