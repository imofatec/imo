type SocialProfileHeaderProps = {
  name: string
}

export default function SocialProfileHeader({ name }: SocialProfileHeaderProps) {
  return (
    <div className="border-b border-white/10 px-6 py-6 lg:px-8">
      <h1 className="text-3xl font-bold text-white lg:text-4xl">{name}</h1>
    </div>
  )
}
