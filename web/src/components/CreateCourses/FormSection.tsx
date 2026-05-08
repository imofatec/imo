import type { ReactNode } from 'react'

type FormSectionProps = {
  title: string
  children: ReactNode
}

export default function FormSection({ title, children }: FormSectionProps) {
  return (
    <div className="rounded-3xl border border-white/10 bg-[#14082f] p-6">
      <h2 className="mb-4 text-xl font-semibold text-white">{title}</h2>
      {children}
    </div>
  )
}
