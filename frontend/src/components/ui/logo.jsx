import headerlogoinicial from "@/assets/headerlogoinicial.svg"
import headerlogoazul from "@/assets/headerlogoazul.svg"
import { useState } from "react"
import { Link } from "react-router-dom"

export function Logo() {

    const [logo, setLogo] = useState(headerlogoinicial)

    function handleLogoHover(e) {
        setLogo(e.type === 'mouseenter' ? headerlogoazul : headerlogoinicial)
    }
    return (

        <Link to="/">
            <img src={logo} onMouseEnter={handleLogoHover} onMouseOut={handleLogoHover} className="mb-4 cursor-pointer"></img>
        </Link>
    )
}