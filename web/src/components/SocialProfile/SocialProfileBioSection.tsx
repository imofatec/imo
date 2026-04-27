type SocialProfileBioSectionProps = {
  bio: string
}

export default function SocialProfileBioSection({ bio }: SocialProfileBioSectionProps) {
  return (
    <section className="rounded-[1.75rem] border border-white/10 bg-[#10052b] p-6">
      <h2 className="text-lg font-semibold text-white">Descrição</h2>
      <p className="mt-4 text-sm leading-7 text-white/78">{bio}</p>
    </section>
  )
}
