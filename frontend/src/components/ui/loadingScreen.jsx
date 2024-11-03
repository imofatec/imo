import { useEffect, useState } from 'react'
import LogoIMO from '@/assets/LogoIMO.svg'
import Spinner from './spinner'


export default function LoadingScreen() {
  const [isVisible, setIsVisible] = useState(true)

  useEffect(() => {
    const timer = setTimeout(() => {
      setIsVisible(false)
    }, 500)

    return () => clearTimeout(timer)
  }, [])

  if (!isVisible) return null

  return (
    <div className="fixed inset-0 flex items-center justify-center bg-custom-header-dark-purple z-50">
      <div className="flex flex-col items-center">
        <img src={LogoIMO} className="h-48 w-48" />
        <Spinner
        className=" w-8 h-8 mt-4 animate-spin"
      />
      </div>
    </div>
  )
}
