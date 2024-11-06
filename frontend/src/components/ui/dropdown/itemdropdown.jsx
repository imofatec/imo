import { AccordionItem } from '@/components/ui/dropdown/accordion'
import { Seletor } from './seletor'

export default function ItemDropdown({ labelItem, isSelected, sizeLabel }) {
  return (
    <>
      <AccordionItem>
        <Seletor
          sizeLabel={sizeLabel}
          conteudo={labelItem}
          isSelected={isSelected}
        ></Seletor>
      </AccordionItem>
    </>
  )
}
