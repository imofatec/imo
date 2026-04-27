export default function SocialProfileSkeleton() {
  return (
    <main className="min-h-screen w-full bg-[#0C0424]">
      <section className="mx-auto w-full max-w-6xl px-4 py-8">
        <div className="overflow-hidden rounded-4xl border border-white/10 bg-[#14082f]">
          <div className="border-b border-white/10 px-6 py-6">
            <div className="h-10 w-44 rounded-2xl bg-white/8" />
          </div>
          <div className="grid gap-8 px-6 py-8 lg:grid-cols-[320px_minmax(0,1fr)]">
            <div className="space-y-4">
              <div className="h-72 rounded-3xl bg-white/8" />
              <div className="h-52 rounded-3xl bg-white/6" />
            </div>
            <div className="space-y-4">
              <div className="h-32 rounded-3xl bg-white/6" />
              <div className="grid gap-4 md:grid-cols-2">
                <div className="h-24 rounded-3xl bg-white/6" />
                <div className="h-24 rounded-3xl bg-white/6" />
                <div className="h-24 rounded-3xl bg-white/6" />
                <div className="h-24 rounded-3xl bg-white/6" />
              </div>
            </div>
          </div>
        </div>
      </section>
    </main>
  )
}
