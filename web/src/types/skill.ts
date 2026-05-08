import type { Category } from '@/types/category'

export type Skill = {
  id: string
  name: string
  category: Category
  isEssential: number
}
