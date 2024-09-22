import { Accordion } from '@/components/ui/dropdown/accordion'
import ItemDropdown from './itemdropdown'

export function Dropdown({categorias}) {
  return (
    <Accordion type="single" collapsible className="text-center ">
      {categorias.map((dado, i) => {
        return (
          <div key={dado.id}>
            <ItemDropdown
              numItem={i}
              labelItem={dado.name}
              contItem={2}
            ></ItemDropdown>
          </div>
        )
      })}
    </Accordion>
  )
}
