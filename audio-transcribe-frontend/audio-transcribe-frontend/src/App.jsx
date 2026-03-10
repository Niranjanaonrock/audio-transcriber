import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import AudioUploader from './assets/AudioUploader.jsx'
import './assets/AudioUploader.css'

function App() {
  const [count, setCount] = useState(0)

  return (
    <div>
     <AudioUploader/>
    </div>
  )
}

export default App;
