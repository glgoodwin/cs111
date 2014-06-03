module type DELAYED = sig
  type 'a delayed
  val makeDelayed : (unit -> 'a) -> 'a delayed
  val force: 'a promise -> 'a
end
