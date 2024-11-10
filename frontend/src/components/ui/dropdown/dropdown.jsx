import { Accordion } from '@/components/ui/dropdown/accordion'
import { NavLink } from 'react-router-dom'
import ItemDropdown from './itemdropdown'

export function Dropdown({
  categorias,
  onCategorySelect,
  selectedCategory,
  url,
}) {
  return (
    <Accordion type="single" collapsible className="text-center">
      {categorias.map((dado, i) => (
        <div key={i} className="cursor-pointer">
          <NavLink
            to={`/${url}/${dado.slug}`}
            className="hover:bg-custom-header-cyan text-blue-600"
            onClick={() => onCategorySelect(dado.slug)}
          >
            <ItemDropdown
              labelItem={dado.name}
              isSelected={selectedCategory === dado.slug}
            />
          </NavLink>
        </div>
      ))}
    </Accordion>
  )
}
