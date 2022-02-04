import axios from "axios";
import {React, useState} from "react";
import { useInput } from "rooks";
import './App.css';
import api from "./api";
import './assets/style.css'

function App() {

  const [isRegisted,setIsRegisterd] = useState(false);
  const [token, setToken] = useState('');

  //User Registration
  const  userName = useInput('');
  const  password = useInput('');
  const  firstName = useInput('');
  const  lastName = useInput('');
  const  email = useInput('');
  const  dataOfBirth = useInput('');
  const  street = useInput('');
  const  city = useInput('');
  const  zipcode = useInput('');
  const state = useInput('');

  // User login
  const  userNameLogIn = useInput('');
  const  passwordLogIn = useInput('');

  
  const handleSubmit = (evt) => {
      evt.preventDefault();
      console.log(firstName);
      alert(`Submitting Name ${firstName.value} ${lastName.value}`);
      
      // axios.defaults.headers.post['Content-Type'] ='application/json;charset=utf-8';
      // const headers = {'Access-Control-Allow-Origin': '*'}

      api.post('/users',{
        'username': userName.value,
        'password': password.value,
        'firstName': firstName.value,
        'lastName': lastName.value,
        'email': email.value,
        'dataOfBirth': dataOfBirth.value,
        'residenceAddress':{
          'street': state.value,
          'city' : city.value,
          'zipCode': zipcode.value,
          'street': street.value
        }
      }).catch((error)=>console.error(error)).
      then((result)=> setToken(result.data.jwt)).
      then(()=> setIsRegisterd(true))
      
  }

const handleLogIn = (evt)=>{
  evt.preventDefault()

  api.post('/login',{
    'username': userNameLogIn.value,
    'password': passwordLogIn.value,
  }).catch((error)=>console.error(error)).
  then(()=> window.location.href = 'http://localhost:8080/')
}


  if(isRegisted)
  return (
    <div class="container">
    <div class="title">Login</div>
    <div class="content">
    <form onSubmit={handleLogIn}>
    <div class="user-details">
          <div class="input-box">
            <span class="details">UserName</span>
            <input type="text" {...userNameLogIn}  placeholder="Enter your Username" required/>
          </div>
          <div class="input-box">
            <span class="details">Password</span>
            <input type="password" {...passwordLogIn} placeholder="Enter your password" required/>
          </div>
          </div>
          <div class="button">
          <input type="submit" value="LogIn"/>
        </div>
        <div class="button">
          <input type="button" onClick={()=>setIsRegisterd(false)} value="Register Now"/>
          </div>
      </form>
      </div>
      </div>
  );
  return (
    <div class="container">
    <div class="title">Registration</div>
    <div class="content">
      <form onSubmit={handleSubmit}>
        <div class="user-details">
          <div class="input-box">
            <span class="details">UserName</span>
            <input type="text" {...userName}  placeholder="Enter your Username" required/>
          </div>
          <div class="input-box">
            <span class="details">Password</span>
            <input type="password" {...password} placeholder="Enter your password" required/>
          </div>
          <div class="input-box">
            <span class="details">Firstname</span>
            <input type="text" {...firstName} placeholder="Enter your firstname" required/>
          </div>
          <div class="input-box">
            <span class="details">Lastname</span>
            <input type="text" {...lastName} placeholder="Enter your lastname" required/>
          </div>
          <div class="input-box">
            <span class="details">Email</span>
            <input type="email" {...email} placeholder="Enter your email" required/>
          </div>
          <div class="input-box">
            <span class="details">Dateofbirth</span>
            <input type="date" {...dataOfBirth} placeholder="Enter your Date of birth" required/>
          </div>
          <div class="input-box">
            <span class="details">street</span>
            <input type="text" {...street} placeholder="Enter your street" required/>
          </div>
          <div class="input-box">
            <span class="details">city</span>
            <input type="text" {...city} placeholder="Enter your city" required/>
          </div>
          <div class="input-box">
            <span class="details">zipcode</span>
            <input type="text" {...zipcode} placeholder="Enter your zipcode" required/>
          </div>
          <div class="input-box">
            <span class="details">state</span>
            <input type="text" {... state} placeholder="Confirm your state" required/>
          </div>
        </div>
        <div class="button">
          <input type="submit" value="Register"/>
        </div>
        <div class="button">
          <input type="button" onClick={()=>setIsRegisterd(true)} value="Log In"/>
          </div>
      </form>
    </div>
  </div>
  );
}

export default App;
