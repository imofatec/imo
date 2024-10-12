import * as React from 'react'
import { Check, ChevronDown } from 'lucide-react'

import { cn } from '@/lib/utils'
import { Button } from '@/components/ui/button'
import {
  Command,
  CommandGroup,
  CommandItem,
  CommandList,
} from '@/components/ui/command'
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from '@/components/ui/popover'

export function SelectList({ categories }) {
  const [open, setOpen] = React.useState(false)
  const [value, setValue] = React.useState('')

  return (
    <Popover open={open} onOpenChange={setOpen}>
      <PopoverTrigger asChild>
        <Button
          variant="outline"
          role="combobox"
          aria-expanded={open}
          className="justify-start bg-custom-blue border border-custom-border-gray  focus:border-white my-2"
        >
          <ChevronDown className="h-6 mr-2 w-6 shrink-0 " />

          {value
            ? categories.find((category) => category.value === value)?.label
            : 'Selecione uma Categoria'}
        </Button>
      </PopoverTrigger>
      <PopoverContent className="p-0">
        <Command className=" ">
          <CommandList>
            <CommandGroup className=" bg-custom-blue border border-custom-border-gray text-white">
              {categories.map((category) => (
                <CommandItem
                  key={category.value}
                  value={category.value}
                  onSelect={(currentValue) => {
                    setValue(currentValue === value ? '' : currentValue)
                    setOpen(false)
                  }}
                >
                  <Check
                    className={cn(
                      'mr-2 h-4 w-4 ',
                      value === categories.value ? 'opacity-100' : 'opacity-0',
                    )}
                  />
                  {category.label}
                </CommandItem>
              ))}
            </CommandGroup>
          </CommandList>
        </Command>
      </PopoverContent>
    </Popover>
  )
}
