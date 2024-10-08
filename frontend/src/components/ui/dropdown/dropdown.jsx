import { Accordion } from '@/components/ui/dropdown/accordion'
import { NavLink } from 'react-router-dom'
import ItemDropdown from './itemdropdown'

export function Dropdown({ categorias }) {
  return (
    <Accordion type="single" collapsible className="text-center">
      {categorias.map((dado, i) => (
        <div key={i}>
          <NavLink
            to={`/categorias/${dado.slug}`}
            className="hover:bg-custom-header-cyan text-blue-600"
          >
            <ItemDropdown labelItem={dado.name} />
          </NavLink>
        </div>
      ))}
    </Accordion>
  )
}
