export default function EstrelaIcon({ desativado }) {

    let corEstrela = desativado ? "#cccccc" : "#ffd500" /* desativado? manda cinza. se não, manda amarelo */

    return <>
        <svg xmlns="http://www.w3.org/2000/svg" width="3.5rem" height="3.5rem" viewBox="0 0 20 -20">
            <path fill={corEstrela} d="m7.325 18.923l1.24-5.313l-4.123-3.572l5.431-.47L12 4.557l2.127 5.01l5.43.47l-4.123 3.572l1.241 5.313L12 16.102z"></path>
        </svg>
    </>
}