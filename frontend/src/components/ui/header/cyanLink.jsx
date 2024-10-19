import { Link} from 'react-router-dom'

export default function CyanLink({name, to}){
    return (
        <Link to={to} className="text-black inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium ring-offset-white transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-slate-950 focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 dark:ring-offset-slate-950 dark:focus-visible:ring-slate-300">
              {name}
            </Link>
    )
}