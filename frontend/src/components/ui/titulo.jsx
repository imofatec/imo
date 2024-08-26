import { useEffect } from "react"

export function Titulo( { titulo } ){
    useEffect(() => {
        document.title = titulo;
      }, [titulo]); 
      return null;
}